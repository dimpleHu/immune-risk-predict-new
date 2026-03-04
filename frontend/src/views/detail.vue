.comments-section {
  background: white;
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0 4px 16px rgba(0,0,0,0.08);
  margin-top: 24px;
}

.comments-section h2 {
  font-size: 24px;
  color: #303133;
  margin: 0 0 24px 0;
  position: relative;
  padding-left: 12px;
}

.comments-section h2::before {
  content: '';
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 4px;
  height: 20px;
  background: #409EFF;
  border-radius: 2px;
}

.comments-list {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.comment-item {
  padding: 24px;
  border: none;
  border-radius: 8px;
  background: #fff;
  box-shadow: 0 2px 12px rgba(0,0,0,0.05);
  transition: all 0.3s ease;
}

.comment-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 16px rgba(0,0,0,0.1);
}

.user-info {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
  padding: 16px;
  background: #f8f9fa;
  border-radius: 8px;
}

.user-info .el-tag {
  padding: 6px 12px;
  font-size: 14px;
  border-radius: 16px;
}

.user-meta {
  width: 100%;
  
  .score-display {
    margin-top: 8px;
    
    .el-rate {
      display: flex;
      justify-content: center;
      
      .el-rate__text {
        font-size: 14px;
        color: #f7ba2a;
      }
    }
  }
}

.comment-content {
  .comment-header {
    margin-bottom: 16px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding-bottom: 12px;
    border-bottom: 1px solid #ebeef5;
    
    .comment-time {
      color: #909399;
      font-size: 13px;
      display: flex;
      align-items: center;
      
      i {
        margin-right: 6px;
        font-size: 16px;
      }
    }
    
    .likes {
      cursor: pointer;
      user-select: none;
      display: flex;
      align-items: center;
      padding: 6px 12px;
      border-radius: 16px;
      background: #f5f7fa;
      transition: all 0.3s ease;
      
      &:hover {
        background: #ecf5ff;
        
        i {
          transform: scale(1.2);
        }
      }
      
      i {
        font-size: 16px;
        color: #909399;
        margin-right: 6px;
        transition: all 0.3s ease;
        
        &.is-liked {
          color: #ff4757;
        }
      }
      
      .likes-count {
        color: #606266;
        font-weight: 500;
      }
    }
  }

  .comment-text {
    font-size: 15px;
    line-height: 1.8;
    color: #303133;
    margin-bottom: 16px;
    padding: 0 4px;
  }
}

.reply-list {
  background: #f8f9fa;
  border-radius: 8px;
  padding: 16px;
  margin: 16px 0;

  .reply-item {
    padding: 12px;
    background: white;
    border-radius: 6px;
    margin-bottom: 12px;
    box-shadow: 0 1px 4px rgba(0,0,0,0.05);
    
    &:last-child {
      margin-bottom: 0;
    }

    .reply-header {
      display: flex;
      align-items: center;
      margin-bottom: 8px;

      .replier-name {
        margin-right: 12px;
        
        .el-tag {
          padding: 4px 8px;
          border-radius: 12px;
        }
      }

      .reply-time {
        color: #909399;
        font-size: 12px;
      }
    }

    .reply-content {
      font-size: 14px;
      color: #606266;
      line-height: 1.6;
      padding: 0 4px;
    }
  }
}

.comment-actions {
  margin-top: 16px;
  display: flex;
  justify-content: flex-end;
  
  .el-button {
    border-radius: 20px;
    padding: 8px 20px;
    
    i {
      margin-right: 4px;
    }
  }
}

.comment-reply-dialog {
  .el-dialog__header {
    padding: 20px;
    text-align: center;
    
    .el-dialog__title {
      font-size: 18px;
      font-weight: 500;
    }
  }
  
  .el-dialog__body {
    padding: 20px 24px;
  }
  
  .el-form-item__label {
    font-weight: 500;
  }
  
  .el-textarea__inner {
    border-radius: 8px;
  }
}

.no-comments {
  text-align: center;
  color: #909399;
  padding: 60px 0;
  font-size: 16px;
  background: #f8f9fa;
  border-radius: 8px;
  
  &::before {
    content: '😊';
    display: block;
    font-size: 32px;
    margin-bottom: 16px;
  }
}

.pagination-container {
  margin-top: 32px;
  
  .el-pagination {
    justify-content: center;
    padding: 0;
    
    .btn-prev,
    .btn-next,
    .number {
      background: white;
      border-radius: 4px;
      margin: 0 4px;
      
      &:hover {
        color: #409EFF;
      }
    }
    
    .active {
      background: #409EFF;
      color: white;
      
      &:hover {
        color: white;
      }
    }
  }
} 